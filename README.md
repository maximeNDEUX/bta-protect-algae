# Protect Algae

A small [Better than Adventure!](https://www.betterthanadventure.net/) mod that stops snow from destroying algae.

In vanilla BTA, when it snows, still water exposed to the sky slowly turns into ice. Algae do not count as a cover, so the water under them freezes too. Algae need water below them, so they break.

This mod adds a gamerule that, when enabled, prevents snow from freezing water that has algae on top of it.

## Usage

The mod adds one gamerule, disabled by default:

| Gamerule       | Default | Effect                                                                 |
|----------------|---------|------------------------------------------------------------------------|
| `protectAlgae` | `false` | When `true`, snow does not freeze water that has algae on top of it. |

Enable it with the command below, or in the gamerules section of the world settings when creating a world:

```
/gamerule protectAlgae true
```

When the gamerule is disabled, the game behaves exactly as in vanilla.

### What it covers

Snow freezes water in two situations, and the gamerule covers both:
- while it is snowing, as the weather updates random columns;
- when a chunk is loaded during heavy snowfall.

### Good to know

- Ice normally spreads from the shore or from existing ice towards the middle of the water. Water under algae never freezes, so ice does not spread past it. On a swamp covered in algae, you may see patches of open water next to the algae.
- Algae can still disappear for other vanilla reasons, such as a lake generated in a new chunk next to them, boats, or players breaking them.

## Requirements

- Better than Adventure! 8.0.1
- Babric / Fabric Loader `0.18.4-bta.11` or newer
- [HalpLibe](https://github.com/Turnip-Labs/bta-halplibe) `6.2.0+8.0.1` or newer

## Installation

1. Install BTA 8.0.1 with the Babric loader. The easiest way is the official Babric instance for Prism Launcher / MultiMC (`bta_fabric_instance_8.0.1.zip` from [babric-instance-repo](https://github.com/Turnip-Labs/babric-instance-repo/releases)), which already includes HalpLibe.
2. Put `protectalgae-<version>.jar` (and HalpLibe, if it is not already there) in the instance's `mods` folder, not in `jarmods`.
3. In multiplayer, the mod must be installed on the server, which handles the weather.

## Building from source

You need a JDK 21 to run Gradle. The mod itself targets Java 17, and Gradle downloads that toolchain if needed.

```
./gradlew build
```

The jar is written to `build/libs/`. To start a development client or server, use `./gradlew runClient` or `./gradlew runServer`.

## License

[CC0 1.0 Universal](LICENSE), public domain.
