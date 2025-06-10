package com.fingerprintjs.android.fingerprint.utils

import com.fingerprintjs.android.fingerprint.DeviceIdResult

public const val ANDROID_ID_HUMAN_NAME: String = "Android ID"
public const val GSF_ID_HUMAN_NAME: String = "GSF ID"
public const val MEDIA_DRM_ID_HUMAN_NAME: String = "Media DRM ID"

public val DeviceIdResult.deviceIdPrettified: String
    get() = this.deviceId.orUnknown()
public val DeviceIdResult.gsfIdPrettified: String
    get() = this.gsfId.orUnknown()
public val DeviceIdResult.androidIdPrettified: String
    get() = this.androidId.orUnknown()
public val DeviceIdResult.mediaDrmIdPrettified: String
    get() = this.mediaDrmId.orUnknown()

private fun String.orUnknown(): String =
    this.takeIf { it.isNotEmpty() } ?: Constants.SIGNAL_UNKNOWN_VALUE
