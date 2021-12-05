package com.astery.wildhack.states.coreTypes

import com.astery.wildhack.states.JobErrorType
import com.astery.wildhackvolunteers.R

class InternetConnectionError():JobErrorType{
    override fun stringId(): Int {
        return R.string.error_internet_connection
    }
}
class SomethingWentWrongError():JobErrorType{
    override fun stringId(): Int {
        return R.string.error_something_went_wrong
    }
}