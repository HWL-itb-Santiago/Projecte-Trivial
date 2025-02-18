package cat.itb.m78.exercices.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.itb.m78.exercices.settings.TrivialSettingsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GamePlayViewModel : ViewModel()
{
    private val _endOfGame = MutableStateFlow(false)
    var endOfGame: StateFlow<Boolean> = _endOfGame.asStateFlow()

    private var _score = MutableStateFlow(0f)
    var score: StateFlow<Float> = _score.asStateFlow()

    private val currentDifficulty = TrivialSettingsManager.get().difficulty.ordinal
    private val currentRounds = TrivialSettingsManager.get().rounds
    private val maxTime = TrivialSettingsManager.get().timeRounds
    private var questionList: List<Question> = listOf()

    private var easyQuestionList = listOf(
        Question("¿Cuántos lados tiene un triángulo?", listOf("1", "2", "3", "4"), 2),
        Question("¿Cuál es el color visible por el ojo humano del sol?", listOf("Blanco", "Azul", "Morado", "Gris"), 3),
        Question("¿Cuántos continentes hay en el mundo?", listOf("1", "2.5", "10", "5"), 3),
        Question("¿En qué continente se encuentra Brasil?", listOf("Europa", "Portugal", "Latinoamérica", "Asia"), 2),
        Question("¿Qué animal es conocido como el rey de la selva?", listOf("Simba", "León", "Hiena", "Ballena"), 1),
        Question("¿Cuántas patas tiene un perro?", listOf("2", "3", "4", "5"), 2),
        Question("¿Cuál es el resultado de 2+2?", listOf("3", "4", "5", "6"), 1),
        Question("¿Qué gas respiramos principalmente en el aire?", listOf("Oxígeno", "Carbono", "Nitrógeno", "Helio"), 2),
        Question("¿Qué planeta es conocido como el planeta rojo?", listOf("Tierra", "Marte", "Venus", "Júpiter"), 1),
        Question("¿Cuál es el animal más grande del mundo?", listOf("Elefante", "Ballena azul", "Tiburón blanco", "Gorila"), 1),
        Question("¿Cuántos colores tiene un semáforo?", listOf("2", "3", "4", "5"), 1),
        Question("¿Cuál es la capital de España?", listOf("Madrid", "Barcelona", "Sevilla", "Valencia"), 0),
        Question("¿Qué dulce fabrican las abejas?", listOf("Azúcar", "Chocolate", "Miel", "Jalea"), 2),
        Question("¿Cuántas horas tiene un día?", listOf("12", "24", "36", "48"), 1),
        Question("¿Cuál es el metal con el que se fabrican la mayoría de los cables eléctricos?", listOf("Plata", "Cobre", "Hierro", "Oro"), 1)
    )

    private var normalQuestionList = listOf(
        Question("¿En qué año llegó el hombre a la Luna?", listOf("1960", "1969", "1975", "1982"), 1),
        Question("¿Cuál es la capital de Francia?", listOf("Roma", "Berlín", "Madrid", "París"), 3),
        Question("¿Quién pintó la Mona Lisa?", listOf("Van Gogh", "Da Vinci", "Picasso", "Rembrandt"), 1),
        Question("¿Cuántos huesos tiene el cuerpo humano adulto?", listOf("200", "206", "214", "190"), 1),
        Question("¿Qué océano es el más grande del mundo?", listOf("Atlántico", "Índico", "Pacífico", "Ártico"), 2),
        Question("¿Quién escribió 'Don Quijote de la Mancha'?", listOf("Cervantes", "García Márquez", "Lorca", "Quevedo"), 0),
        Question("¿Cuál es la moneda oficial de Japón?", listOf("Dólar", "Yen", "Euro", "Peso"), 1),
        Question("¿Quién desarrolló la teoría de la relatividad?", listOf("Newton", "Einstein", "Tesla", "Curie"), 1),
        Question("¿Qué país tiene forma de bota en el mapa?", listOf("España", "Italia", "Portugal", "Grecia"), 1),
        Question("¿Qué energía es obtenida a partir del sol?", listOf("Eólica", "Hidráulica", "Solar", "Geotérmica"), 2),
        Question("¿Cómo se llama el proceso mediante el cual las plantas producen su alimento?", listOf("Fotosíntesis", "Respiración", "Germinación", "Oxidación"), 0),
        Question("¿Quién es el autor de 'Hamlet'?", listOf("Shakespeare", "Goethe", "Molière", "Dante"), 0),
        Question("¿Cuál es el río más largo del mundo?", listOf("Amazonas", "Nilo", "Yangtsé", "Misisipi"), 1),
        Question("¿Qué instrumento musical tiene teclas blancas y negras?", listOf("Violín", "Guitarra", "Piano", "Trompeta"), 2),
        Question("¿Cuál es el planeta más grande del sistema solar?", listOf("Marte", "Saturno", "Júpiter", "Urano"), 2)
    )

    private var hardQuestionList = listOf(
        Question("¿Cuál es el metal más abundante en la corteza terrestre?", listOf("Hierro", "Aluminio", "Cobre", "Plata"), 1),
        Question("¿Qué matemático desarrolló el teorema que lleva su nombre sobre los triángulos rectángulos?", listOf("Arquímedes", "Pitágoras", "Euclides", "Newton"), 1),
        Question("¿Cuál es el gas más abundante en la atmósfera terrestre?", listOf("Oxígeno", "Nitrógeno", "Dióxido de carbono", "Hidrógeno"), 1),
        Question("¿Qué tipo de ondas utiliza el radar?", listOf("Ultravioleta", "Sonoras", "Electromagnéticas", "Infrarrojas"), 2),
        Question("¿Quién fue el primer emperador de Roma?", listOf("Julio César", "Augusto", "Nerón", "Trajano"), 1),
        Question("¿Cuál es el número atómico del oxígeno?", listOf("6", "7", "8", "9"), 2),
        Question("¿Quién formuló la ley de la gravitación universal?", listOf("Kepler", "Copérnico", "Newton", "Galileo"), 2),
        Question("¿Qué filamento se usaba en las primeras bombillas incandescentes?", listOf("Cobre", "Plomo", "Tungsteno", "Carbón"), 3),
        Question("¿Cuál es el elemento químico más ligero?", listOf("Helio", "Oxígeno", "Hidrógeno", "Nitrógeno"), 2),
        Question("¿En qué año terminó la Segunda Guerra Mundial?", listOf("1940", "1943", "1945", "1950"), 2),
        Question("¿Cuál es la constante matemática conocida como la base de los logaritmos naturales?", listOf("π", "e", "φ", "i"), 1),
        Question("¿Cuál es el hueso más largo del cuerpo humano?", listOf("Radio", "Húmero", "Fémur", "Tibia"), 2),
        Question("¿Cuál es la única mamífero capaz de volar?", listOf("Águila", "Murciélago", "Colibrí", "Ardilla voladora"), 1),
        Question("¿Qué científico descubrió la radiactividad?", listOf("Curie", "Rutherford", "Bohr", "Einstein"), 0),
        Question("¿Cuál es la velocidad de la luz en el vacío?", listOf("100,000 km/s", "150,000 km/s", "299,792 km/s", "1,000,000 km/s"), 2)
    )

    init {
        questionList = when (currentDifficulty)
        {
            0 -> easyQuestionList.shuffled()
            1 -> normalQuestionList.shuffled()
            2 -> hardQuestionList.shuffled()
            else -> emptyList()
        }
        timer()
    }

    private val weightQuestion = 100 / currentRounds

    private var timerJob: Job? = null

    private var _currentQuestion = MutableStateFlow(0)
    var currentQuestion: StateFlow<Int> = _currentQuestion.asStateFlow()

    private var _currentTime = MutableStateFlow(0f)
    var currentTime: StateFlow<Float> = _currentTime.asStateFlow()



    fun printQuestion(currentRound: Int): String
    {
        return questionList[currentRound].questionName
    }

    fun printAnswers(index: Int): String
    {
        return  questionList[currentQuestion.value].questionAnswers[index]
    }

    fun nextQuestion(playerAnswer: Int)
    {
        validatedAnswer(playerAnswer)
        if (currentQuestion.value >= currentRounds - 1)
        {
            _endOfGame.value = true
        }
        else
        {
            _currentQuestion.value += 1
            rebootTimer()
        }
    }

    private fun timer()
    {
        timerJob?.cancel()
        timerJob = viewModelScope.launch(Dispatchers.Main)
        {
            for (i in 0 ..maxTime) {
                _currentTime.value = (i.toFloat() / maxTime)
                delay(1000)
            }
            nextQuestion(-1)
        }
    }

    private fun rebootTimer()
    {
        timerJob?.cancel()
        _currentTime.value = 0f
        timer()
    }

    private fun validatedAnswer(playerAnswer: Int)
    {
        if (questionList[currentQuestion.value].answer == playerAnswer) {
            _score.value += weightQuestion
        }
        PlayerScore.updateScore(PlayerScore.getScore().copy(score = _score.value))
    }
}

data class Question(
    val questionName: String,
    val questionAnswers: List<String>,
    val answer: Int
)