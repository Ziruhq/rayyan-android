package com.fingerprintjs.android.fingerprint.info_providers


import android.os.Build
import com.fingerprintjs.android.fingerprint.tools.DeprecationMessages
import com.fingerprintjs.android.fingerprint.tools.parsers.parseCpuInfo
import com.fingerprintjs.android.fingerprint.tools.threading.safe.safeWithTimeout
import java.io.File
import java.util.Scanner


public data class CpuInfo(
    val commonInfo: List<Pair<String, String>>,
    // except processor : x pairs. index in list may be considered as an index of a processor.
    val perProcessorInfo: List<List<Pair<String, String>>>,
) {
    public companion object {
        public val EMPTY: CpuInfo = CpuInfo(
            commonInfo = emptyList(),
            perProcessorInfo = emptyList(),
        )
    }
}

@Deprecated(message = DeprecationMessages.UNREACHABLE_SYMBOL_UNINTENDED_PUBLIC_API)
public interface CpuInfoProvider {
    public fun cpuInfo(): Map<String, String>
    public fun cpuInfoV2(): CpuInfo
    public fun abiType(): String
    public fun coresCount(): Int
}

internal class CpuInfoProviderImpl :
    CpuInfoProvider {
    override fun cpuInfo(): Map<String, String> {
        return safeWithTimeout {
            getCpuInfo()
        }.getOrDefault(emptyMap())
    }

    override fun cpuInfoV2(): CpuInfo {
        return safeWithTimeout {
            getCpuInfoV2()
        }.getOrDefault(CpuInfo.EMPTY)
    }

    @Suppress("DEPRECATION")
    override fun abiType(): String {
        return safeWithTimeout {
            Build.SUPPORTED_ABIS[0]!!
        }.getOrDefault("")
    }

    override fun coresCount(): Int {
        return safeWithTimeout {
            Runtime.getRuntime()!!.availableProcessors()
        }.getOrDefault(0)
    }

    private fun getCpuInfo(): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()
        Scanner(File(CPU_INFO_PATH)).use { s ->
            while (s.hasNextLine()) {
                val cpuInfoValues = s.nextLine()!!.split(KEY_VALUE_DELIMITER)
                if (cpuInfoValues.size > 1) map[cpuInfoValues[0].trim { it <= ' ' }] =
                    cpuInfoValues[1].trim { it <= ' ' }
            }
        }

        return map
    }

    private fun getCpuInfoV2(): CpuInfo {
        val cpuInfoContents = File(CPU_INFO_PATH).readText()
        return parseCpuInfo(cpuInfoContents)
    }
}

private const val CPU_INFO_PATH = "/proc/cpuinfo"
private const val KEY_VALUE_DELIMITER = ": "
