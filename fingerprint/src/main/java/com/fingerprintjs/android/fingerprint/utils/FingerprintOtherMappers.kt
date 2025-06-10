package com.fingerprintjs.android.fingerprint.utils

import com.fingerprintjs.android.fingerprint.Fingerprinter
import com.fingerprintjs.android.fingerprint.signal_providers.StabilityLevel

public val Fingerprinter.Version.description: String
    get() = "V${this.ordinal + 1}"

public val StabilityLevel.description: String
    get() = this.name.lowercase().replaceFirstChar { it.uppercase() }