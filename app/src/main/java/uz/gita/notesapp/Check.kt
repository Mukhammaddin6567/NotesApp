package uz.gita.notesapp

class Check {

    fun isPhone(phone: String): Boolean {
        return phone.startsWith("+998") &&
                phone.substring(1).matches("^[0-9]*$".toRegex()) &&
                phone.length == 13
    }

}