# GenBucket Plugin

A modular, optimized, and highly configurable GenBucket plugin designed for modern Factions servers. Supports horizontal and vertical generation with advanced control over behavior, restrictions, and performance.

---

## 📦 Module Overview

This project is built using a multi-module Maven structure:

- **api/** – Public-facing API used by external plugins to integrate with GenBucket.
- **core/** – Core internal logic including gen mechanics, validation, and combat checks.
- **plugin/** – Final Spigot plugin module that packages the API and core logic for deployment.

---

## ⚙️ Configuration

All plugin settings are located in `plugins/GenBucket/config.yml`.

### 🔧 General Settings

| Setting | Description                                               |
|--------|-----------------------------------------------------------|
| `optimize-block-place` | Enables optimized block placing. Disable if errors occur. |
| `disable-in-combat` | Prevents gen placement during combat.                     |
| `replace-bucket` | Replaces and consumes bucket in inventory after each use. |
| `click-menu` | Enables GUI-based bucket selection (`/gb`).               |
| `default-faction-name` | Name used for unclaimed land (usually “wilderness”).      |
| `allow-wilderness-gen` | If false, disables gen usage in unclaimed areas.          |
| `max-buckets` | Max simultaneous iterations before gen pauses.            |
| `clear-drops` | Automatically deletes dropped GenBucket items.            |
| `tick-speed` | Tick delay (5 ticks = 0.25s) between generation steps.    |
| `vertical-switch` | Y-level to determine whether vertical gens go up or down. |
| `use-replace-command` | Allows non-OPs to use `/gb` menu.                         |

### ⛔ Faction Restrictions

```yaml
blocked-factions:
  - "Warzone"
```

### 🌍 World Whitelisting (for `/gen test`)

```yaml
test-command:
  whitelisted-worlds:
    - "someworldnamehere"
```

---

## 🧱 GenBucket Types

Each GenBucket type is configured under `genbuckets:` with flexible options:

### Example: `CobbleVertical`

```yaml
CobbleVertical:
  bypass-lava-water: true
  patch: false
  material: COBBLESTONE
  menu-slot: 12
  bucket-cost: 200
  bucket-item: LAVA_BUCKET
  bucket-name: "&7&lCobblestone Vertical"
  bucket-type: Vertical
  bucket-lore:
    - "&7&o(Placing this bucket will generate a wall)"
    - ""
    - "&2&l➥ &a&lBUCKET TYPE: &7[TYPE]"
    - "&2&l➥ &a&lBUCKET COST: &7$[COST]"
```

### Supported Keys

| Key | Description |
|-----|-------------|
| `bypass-lava-water` | Allows generation through liquid blocks. |
| `patch` | Allows replacing its own material. |
| `material` | Material that gets placed during generation. |
| `bucket-type` | Can be `Vertical` or `Horizontal`. |
| `bucket-size` | (Optional) Only for horizontal gens. |
| `menu-slot` | Slot in the `/gb` GUI. |
| `bucket-cost` | Vault-based cost per use. |
| `bucket-item` | Item used to initiate generation. |
| `bucket-name` | Display name shown to players. |
| `bucket-lore` | List of lore lines (supports placeholders). |

---

## 🧩 Commands

| Command | Description | Permission |
|---------|-------------|------------|
| `/gb` | Opens the GenBucket GUI | `genbucket.menu` |
| `/gen test` | Debug/test command (whitelisted worlds only) | `genbucket.test` |

---

## 💬 Messages

Customizable from the config:

```yaml
messages:
  error-permission: "&cNot enough permissions."
  error-no-money: "&cYou do not have enough money to place a GenBucket!"
  error-whitelist: "&cThis world is currently not whitelisted for the /gen test command!"
  error-not-player: "&cOnly players may execute this command"
  gen-wilderness: "&c You cannot use a GenBucket in Wilderness!"
  player-cant-gen-here: "&c You cannot use a GenBucket here!"
```

---

## 🧪 Development

To compile the plugin:

```bash
mvn clean install
```

Use JDK 21 or newer and Maven 3.5+ (tested with Maven 3.9).
The Spigot server artifacts and remapping files for 1.8.8, 1.21.4, and 1.21.6
must be installed locally using Spigot BuildTools.

Set the plugin version once in the root `pom.xml` under `<revision>`.
All modules and the packaged `plugin.yml` use that version. Override it for a
single build with `mvn "-Drevision=1.9-SNAPSHOT" clean install`.

Set XSeries once under `<xseries.version>` in the root `pom.xml`.
Its Maven version is `13.7.1`, without the Git tag prefix `v`.

Deployable JAR will be in `plugin/target/GenBucket-<revision>.jar`.

---

## 🔗 Dependencies

- Spigot 1.21.4
- Vault (Economy support)
- Factions (Saber-Factions)
- WorldGuard (Optional)
- CombatLogX (Optional, for anti-combat placement)

---

## ✅ Features

- Full GUI support with configurable layout
- Economy integration (Vault)
- Combat prevention
- Wilderness control
- Gen types: Vertical, Horizontal, Printable, Patch
- Multi-world support
- Tick-speed optimization
- Pseudo-gen logic (patch)
- Smart direction switching at Y-level

---

## 📞 Support

If you need assistance, feel free to:

- 📬 Join our [Discord community](https://discord.gg/3mvJq5f) for live support and updates.
- 🐛 Open an issue on our repository if you encounter a bug or need help with configuration.

We're here to help!
