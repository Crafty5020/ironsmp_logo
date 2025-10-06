# Iron SMP Logo

This is a Fabric mod that with a command will spawn in all maps for the Iron SMP Logo.

> [!NOTE]
> This plugin will work with any map art the only requirement is to run two little commands
>
> OP is required to get maps.

## Required Mods
  1. Fabric Api

## How to use?

1. To add support to your logo just run this two commands.

  ```
    /iron_logo  change_maps_spawned_alr 0 # Change this to the amount of maps spawned already by players and you.
    /iron_logo change_map_amount 16 # Change this to the amount of maps required for you're map logo.
  ```

1. Add the map files in the world folder.

## How to compile?

1. Download gradle 9.1.0 and java 21
2. Run `./gradlew build` in the project directory
3. Find the build in `build/libs`
