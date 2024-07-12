sealed interface DiaryResult<T> {

    data class Error<T>(val message: String?): DiaryResult<T?>

    data class Success<T>(val data: T?): DiaryResult<T?>
}