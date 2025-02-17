package cat.itb.m78.exercices.settings

enum class TrivialSubject{Easy, Normal, Hard}

data class TrivialSettings(
    val difficulty: TrivialSubject = TrivialSubject.Easy,
    val rounds: Int = 5,
    val timeRounds: Int = 5
)

data object TrivialSettingsManager{
    private var settings = TrivialSettings()
    fun update(newSettings: TrivialSettings)
    {
        settings = newSettings
    }
    fun get() = settings
}
