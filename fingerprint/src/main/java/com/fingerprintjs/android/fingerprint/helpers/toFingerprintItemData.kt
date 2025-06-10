package com.fingerprintjs.android.fingerprint.helpers

import android.annotation.SuppressLint
import com.fingerprintjs.android.fingerprint.Fingerprinter
import com.fingerprintjs.android.fingerprint.fingerprinting_signals.FingerprintingSignal
import com.fingerprintjs.android.fingerprint.signal_providers.StabilityLevel
import com.fingerprintjs.android.fingerprint.utils.*

@SuppressLint("DiscouragedApi")
internal fun FingerprintingSignal<*>.toFingerprintItemData(): FingerprintItemData {
    return FingerprintItemData(
            signalName = this.humanName,
            signalData = this.jsonifiableValue,
            stabilityLevel = this.info.stabilityLevel,
            versionStart = this.info.addedInVersion,
            versionEnd = this.info.removedInVersion ?: Fingerprinter.Version.latest,
    )
}

public data class FingerprintItemData(
        val signalName: String,
        val signalData: Any,
        val stabilityLevel: StabilityLevel,
        val versionStart: Fingerprinter.Version,
        val versionEnd: Fingerprinter.Version
) {
    public companion object {
        public val EXAMPLE: FingerprintItemData =
                FingerprintItemData(
                        signalName = "Android ID",
                        signalData = "{}",
                        stabilityLevel = StabilityLevel.STABLE,
                        versionStart = Fingerprinter.Version.V_2,
                        versionEnd = Fingerprinter.Version.V_5,
                )
    }
}
