package com.github.junhee8649.androidstudy17.week4.Unscramble.data

// 게임당 최대 단어 수를 표시
const val MAX_NO_OF_WORDS = 10
// 단어를 맞출 때마다 플레이어가 얻는 점수
const val SCORE_INCREASE = 20

// 코드에 문자열을 하드코딩하는 것은 권장되지 않습니다.
// 쉽게 현지화하기 위해 strings.xml에 문자열을 추가하세요.
// 이 예시 앱에서는 편의상, 그리고 개발자가 앱 아키텍처에 집중할 수 있도록 문자열이 하드코딩되어 있습니다.

// Set with all the words for the Game
val allWords: Set<String> =
    setOf(
        "animal",
        "auto",
        "anecdote",
        "alphabet",
        "all",
        "awesome",
        "arise",
        "balloon",
        "basket",
        "bench",
        "best",
        "birthday",
        "book",
        "briefcase",
        "camera",
        "camping",
        "candle",
        "cat",
        "cauliflower",
        "chat",
        "children",
        "class",
        "classic",
        "classroom",
        "coffee",
        "colorful",
        "cookie",
        "creative",
        "cruise",
        "dance",
        "daytime",
        "dinosaur",
        "doorknob",
        "dine",
        "dream",
        "dusk",
        "eating",
        "elephant",
        "emerald",
        "eerie",
        "electric",
        "finish",
        "flowers",
        "follow",
        "fox",
        "frame",
        "free",
        "frequent",
        "funnel",
        "green",
        "guitar",
        "grocery",
        "glass",
        "great",
        "giggle",
        "haircut",
        "half",
        "homemade",
        "happen",
        "honey",
        "hurry",
        "hundred",
        "ice",
        "igloo",
        "invest",
        "invite",
        "icon",
        "introduce",
        "joke",
        "jovial",
        "journal",
        "jump",
        "join",
        "kangaroo",
        "keyboard",
        "kitchen",
        "koala",
        "kind",
        "kaleidoscope",
        "landscape",
        "late",
        "laugh",
        "learning",
        "lemon",
        "letter",
        "lily",
        "magazine",
        "marine",
        "marshmallow",
        "maze",
        "meditate",
        "melody",
        "minute",
        "monument",
        "moon",
        "motorcycle",
        "mountain",
        "music",
        "north",
        "nose",
        "night",
        "name",
        "never",
        "negotiate",
        "number",
        "opposite",
        "octopus",
        "oak",
        "order",
        "open",
        "polar",
        "pack",
        "painting",
        "person",
        "picnic",
        "pillow",
        "pizza",
        "podcast",
        "presentation",
        "puppy",
        "puzzle",
        "recipe",
        "release",
        "restaurant",
        "revolve",
        "rewind",
        "room",
        "run",
        "secret",
        "seed",
        "ship",
        "shirt",
        "should",
        "small",
        "spaceship",
        "stargazing",
        "skill",
        "street",
        "style",
        "sunrise",
        "taxi",
        "tidy",
        "timer",
        "together",
        "tooth",
        "tourist",
        "travel",
        "truck",
        "under",
        "useful",
        "unicorn",
        "unique",
        "uplift",
        "uniform",
        "vase",
        "violin",
        "visitor",
        "vision",
        "volume",
        "view",
        "walrus",
        "wander",
        "world",
        "winter",
        "well",
        "whirlwind",
        "x-ray",
        "xylophone",
        "yoga",
        "yogurt",
        "yoyo",
        "you",
        "year",
        "yummy",
        "zebra",
        "zigzag",
        "zoology",
        "zone",
        "zeal"
    )