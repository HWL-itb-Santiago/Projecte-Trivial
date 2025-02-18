package cat.itb.m78.exercices.viewModels

data class Player(
    val score: Float = 0f
)

data object PlayerScore
{
    private var playerScore = Player()
    fun updateScore(newPlayerScore: Player)
    {
        playerScore = newPlayerScore
    }
    fun getScore() = playerScore
}