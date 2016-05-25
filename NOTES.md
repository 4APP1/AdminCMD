Notes/Outline
--------

MODULE
  - CORE
    - FileManager
    - DatabaseManager
    - AddonManager
  - API
    - CommandManager
    - EventManager
    - Server
    - Registry
    - CORE
      - Commands
      - Listeners
      - Features

ArgumentParsing
- Filters
  - Components
    - [0:world] - The potential arguments
      - <> specifies optional arguments
      - 0-n specifies the argument index required
      - key words such as "player", "world", and "location" will resolve to optional objects
      - Examples: [0], <0>, [0:world], <0:world>, [world], <world>
    - {console.w:world} - The potential flags
      - {[]} specifies required flags
      - {<>} specifies optional flags
      - key words such as "console", "player", or "block" will require those sources
      - a-z specifies the flag char required
      - key words such as "player", "world", and "location" will resolve to optional objects
      - Examples: {[w]}, {<w>}, {[w:world]}, {<w:world>}, {[console.w]}, {[console.w:world]}