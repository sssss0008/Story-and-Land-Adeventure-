package com.example.data

import com.example.model.*

object StoryData {

    val fairyTaleHeroes = listOf(
        FairyTaleOption("princess", "Brave Princess", "👸", "Clever, kind, and courageous leader"),
        FairyTaleOption("prince", "Noble Prince", "🤴", "Friendly hero who loves solving puzzles"),
        FairyTaleOption("wizard", "Curious Wizard", "🧙‍♂️", "Carries a glowing wand and spellbook"),
        FairyTaleOption("explorer", "Wilderness Explorer", "🧭", "Equipped with compass and adventure spirit"),
        FairyTaleOption("knight", "Silver Knight", "🛡️", "Protector of all peaceful kingdom creatures"),
        FairyTaleOption("inventor", "Clever Inventor", "⚙️", "Builds flying gliders and gadget belts"),
        FairyTaleOption("animal", "Forest Fox Hero", "🦊", "Swift, witty, and loyal friend")
    )

    val fairyTaleWorlds = listOf(
        FairyTaleOption("enchanted_forest", "Enchanted Forest", "🌲", "Luminous trees and whispering fireflies"),
        FairyTaleOption("magic_kingdom", "Magic Kingdom", "🏰", "Golden spires and sparkling rainbow rivers"),
        FairyTaleOption("underwater_city", "Underwater City", "🐬", "Coral palaces with friendly sea turtles"),
        FairyTaleOption("cloud_kingdom", "Cloud Kingdom", "☁️", "Fluffy floating castles in the sky"),
        FairyTaleOption("space_castle", "Space Castle", "🚀", "Starlit observatory among glowing nebulas"),
        FairyTaleOption("mystery_island", "Mystery Island", "🏝️", "Ancient lighthouses and hidden treasure caverns")
    )

    val fairyTaleCompanions = listOf(
        FairyTaleOption("dragon", "Baby Dragon", "🐉", "Breathes warm stardust bubbles"),
        FairyTaleOption("fairy", "Glimmer Fairy", "🧚", "Lights dark paths with sparkle dust"),
        FairyTaleOption("robot", "Beep the Robot", "🤖", "Has a built-in map and tools"),
        FairyTaleOption("cat", "Talking Cat", "🐱", "Wears boots and tells clever riddles"),
        FairyTaleOption("giant", "Friendly Giant", "🦾", "Lifts huge rocks and builds tall bridges")
    )

    val fairyTaleProblems = listOf(
        FairyTaleOption("lost_treasure", "The Lost Treasure", "💎", "The royal jewels were hidden by ancient wind"),
        FairyTaleOption("missing_crown", "The Missing Crown", "👑", "The kingdom crown vanished from the tower"),
        FairyTaleOption("magic_door", "The Locked Magic Door", "🚪", "Requires three musical notes to open"),
        FairyTaleOption("lost_friend", "A Lost Friend", "🐾", "A little creature wandered into the deep glen"),
        FairyTaleOption("secret_map", "The Secret Map", "🗺️", "Half of the ancient parchment was carried away")
    )

    val fairyTaleEndings = listOf(
        FairyTaleOption("happy", "A Joyful Celebration", "🎉", "Everyone joins together in laughter and music"),
        FairyTaleOption("surprise", "A Surprising Discovery", "✨", "The real treasure was a secret garden of stars"),
        FairyTaleOption("heroic", "A Legendary Triumph", "🏆", "The whole land honors the hero's kindness"),
        FairyTaleOption("funny", "A Silly Twist", "🎈", "The runaway mystery was caused by playful puppies")
    )

    val allStories = listOf(
        Story(
            id = "dragon_moonlight",
            title = "The Dragon of Moonlight Valley",
            subtitle = "A heartwarming tale of bravery and friendship under the moonlit skies.",
            category = StoryCategory.FANTASY,
            readingTimeMinutes = 6,
            xpReward = 25,
            starsReward = 6,
            coverEmoji = "🐉",
            primaryColorHex = 0xFF7C3AED,
            isFeatured = true,
            moral = "Kindness and courage can melt even the coldest fear.",
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "The Whispering Glen",
                    paragraphs = listOf(
                        "High in the misty crags of Moonlight Valley, the night breeze smelled of pine needles and silver dew.",
                        "Little Oliver tightened his woolen cloak and looked up at the purple crescent moon.",
                        "Rumors said that a giant beast lived atop the highest peak, guarding an ancient secret.",
                        "Oliver wasn't looking for treasure. He was searching for his grandfather's lost lantern, which had vanished near the glowing falls."
                    ),
                    sceneEmoji = "🌙"
                ),
                StoryChapter(
                    chapterNumber = 2,
                    title = "Footprints of Fire",
                    paragraphs = listOf(
                        "As Oliver climbed past the silver pine trees, he noticed soft golden prints on the stone.",
                        "They did not look frightening. In fact, tiny blue spark-flowers bloomed wherever the paws touched.",
                        "Suddenly, a tiny sneeze shook the nearby bushes!",
                        "A small puff of warm vanilla-scented smoke floated into the cool evening air."
                    ),
                    sceneEmoji = "🔥"
                ),
                StoryChapter(
                    chapterNumber = 3,
                    title = "The Friendly Dragon",
                    paragraphs = listOf(
                        "Oliver parted the ferns and gasped in delight.",
                        "Curled up like a giant emerald cat was not a monster, but a baby dragon named Pip.",
                        "Pip had big round turquoise eyes and silver scales that caught the moonlight like mirrors.",
                        "Beside Pip was grandfather's lantern, keeping the baby dragon's wing warm while he slept.",
                        "Pip opened one sleepy eye, looked at Oliver, and let out a gentle, happy purr."
                    ),
                    sceneEmoji = "🐉"
                ),
                StoryChapter(
                    chapterNumber = 4,
                    title = "The Bridge of Stars",
                    paragraphs = listOf(
                        "Oliver offered Pip a handful of sweet dried apples from his pack.",
                        "Pip crunched them happily, spreading his shimmering wings with a whoosh of warm air.",
                        "Together, they needed to cross the Star Chasm to return to the village before dawn.",
                        "With a gentle flutter, Pip guided Oliver safely across the glittering stone bridge."
                    ),
                    sceneEmoji = "🌉"
                ),
                StoryChapter(
                    chapterNumber = 5,
                    title = "The Village Welcome",
                    paragraphs = listOf(
                        "When the villagers saw the glowing light approaching, they held their breath.",
                        "Instead of roaring flames, they saw Oliver riding beside a playful dragon blowing warm starlight bubbles.",
                        "The children clapped and grandfather laughed with joyful tears, holding his warm lantern high.",
                        "Moonlight Valley was never afraid of dragons ever again."
                    ),
                    sceneEmoji = "🏰"
                ),
                StoryChapter(
                    chapterNumber = 6,
                    title = "Guardians of the Valley",
                    paragraphs = listOf(
                        "From that night onward, Oliver and Pip were the official protectors of the valley.",
                        "Whenever a traveler lost their way in the fog, a gentle green dragon and a golden lantern guided them home.",
                        "And under the full moon, if you listen closely, you can hear Pip's peaceful purr echoing across the mountains."
                    ),
                    sceneEmoji = "🌟"
                )
            )
        ),
        Story(
            id = "secret_door",
            title = "The Secret Door in the Forest",
            subtitle = "Today's magical discovery deep inside the Whispering Woods.",
            category = StoryCategory.MAGIC,
            readingTimeMinutes = 5,
            xpReward = 20,
            starsReward = 5,
            coverEmoji = "🚪",
            primaryColorHex = 0xFF0D9488,
            isFeatured = true,
            moral = "Curiosity unlocks wonders you never imagined.",
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "The Wooden Keyhole",
                    paragraphs = listOf(
                        "Mia loved discovering secret corners of the old oak woods behind her grandmother's garden.",
                        "Today, buried beneath velvet green moss on a thousand-year-old tree, she spotted an iron keyhole.",
                        "It hummed like a sleepy bumblebee and glowed with a soft golden rim.",
                        "Mia reached into her pocket and pulled out the brass key she had found that morning in the attic."
                    ),
                    sceneEmoji = "🗝️"
                ),
                StoryChapter(
                    chapterNumber = 2,
                    title = "Into the Wonder Glen",
                    paragraphs = listOf(
                        "With a smooth click, the bark of the giant oak slid open like a palace door.",
                        "Inside was not dark wood, but a spiral staircase made of polished river glass!",
                        "Mia stepped through into a sunlit meadow that defied all logic.",
                        "Miniature crystal castles stood among giant clover flowers, and butterflies carried tiny woven messages between ladybugs."
                    ),
                    sceneEmoji = "✨"
                ),
                StoryChapter(
                    chapterNumber = 3,
                    title = "The Keeper of Stories",
                    paragraphs = listOf(
                        "A tiny hedgehog wearing round spectacles bowed politely from a mushroom stool.",
                        "'Welcome, Mia,' said the hedgehog in a cheerful squeak. 'We have waited for the next Story Explorer.'",
                        "He handed her a blank leather journal with a feather quill that moved by itself.",
                        "'Whatever you imagine in your heart, write it here, and our forest blossoms with new colors.'",
                        "Mia smiled, dipped the quill, and wrote: 'Today, the sweetest adventure begins.'"
                    ),
                    sceneEmoji = "🦔"
                )
            )
        ),
        Story(
            id = "pirate_seas",
            title = "Captain Barnaby & the Star Compass",
            subtitle = "Sail the glowing seas with Captain Barnaby to find the Island of Wonders!",
            category = StoryCategory.ADVENTURE,
            readingTimeMinutes = 7,
            xpReward = 30,
            starsReward = 8,
            coverEmoji = "🏴‍☠️",
            primaryColorHex = 0xFF0284C7,
            isFeatured = true,
            moral = "Teamwork and honesty turn every storm into sunshine.",
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "Setting Sail at Twilight",
                    paragraphs = listOf(
                        "The Sea Sprite was the fastest wooden cutter on the turquoise waters of the Archipelago.",
                        "Captain Barnaby, a merry sailor with a feathered tricorn hat and a telescope, adjusted the wheel.",
                        "Beside him, Polly the clever parrot squawked: 'Check the sails! The Star Compass points north-by-moon!'",
                        "In Barnaby's hand, the brass compass didn't point toward magnetic poles, but toward the nearest mystery."
                    ),
                    sceneEmoji = "⛵"
                ),
                StoryChapter(
                    chapterNumber = 2,
                    title = "The Singing Sirens",
                    paragraphs = listOf(
                        "A mysterious mist wrapped around the bow, and gentle melodious flute notes drifted over the waves.",
                        "Barnaby lowered the anchor near Coral Reef Shoals.",
                        "Instead of scary sea monsters, three friendly dolphins wearing seashell necklaces surfaced.",
                        "They showed Barnaby a secret underwater cavern where pirate maps had been safe for centuries."
                    ),
                    sceneEmoji = "🐬"
                ),
                StoryChapter(
                    chapterNumber = 3,
                    title = "The Treasure of Wisdom",
                    paragraphs = listOf(
                        "Deep in the cave, the chest lay under a beam of starlight.",
                        "Barnaby lifted the heavy wooden lid with a creak.",
                        "Inside was not gold or silver, but hundreds of ancient illustrated scrolls containing lost knowledge!",
                        "'This,' whispered Barnaby with wide eyes, 'is the greatest treasure in all seven seas: the stories of the world.'"
                    ),
                    sceneEmoji = "💎"
                )
            )
        ),
        Story(
            id = "magic_kingdom",
            title = "The Lost Crown of Solaria",
            subtitle = "Restore light to the majestic castle towers of the Magic Kingdom.",
            category = StoryCategory.FANTASY,
            readingTimeMinutes = 8,
            xpReward = 35,
            starsReward = 9,
            coverEmoji = "🏰",
            primaryColorHex = 0xFFF59E0B,
            isFeatured = true,
            moral = "True leadership shines with empathy and listening.",
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "The Dimming Spires",
                    paragraphs = listOf(
                        "For centuries, the Golden Castle of Solaria radiated pure warm sunlight day and night.",
                        "Prince Leo looked up at the central spire. The eternal sun-gem had lost its golden shine.",
                        "The Royal Archivist brought out the ancient decree: 'When the crown rests with the rightful heart, light shall return.'",
                        "Leo packed his traveling satchel, ready to visit the four corners of the kingdom."
                    ),
                    sceneEmoji = "🏰"
                ),
                StoryChapter(
                    chapterNumber = 2,
                    title = "The Fairy Village of Whisper Brook",
                    paragraphs = listOf(
                        "His first stop was Whisper Brook, where fairies lived inside hollowed cherry blossoms.",
                        "The Fairy Queen greeted Leo with a riddle: 'What shines brightest when shared, yet leaves no shadow?'",
                        "Leo smiled gently. 'A kind deed,' he answered.",
                        "The Queen smiled and gifted him a vial of golden starlight nectar to light his path."
                    ),
                    sceneEmoji = "🧚"
                ),
                StoryChapter(
                    chapterNumber = 3,
                    title = "The Crown Restored",
                    paragraphs = listOf(
                        "Guided by the starlight, Leo discovered the crown safely nestled in the nest of an injured phoenix chick.",
                        "Rather than seizing the crown, Leo nursed the little bird with his warm potion.",
                        "Grateful, the phoenix fluttered its fiery wings and placed the glowing crown in Leo's hands.",
                        "Golden warmth surged across Solaria once again, brighter and warmer than ever before."
                    ),
                    sceneEmoji = "👑"
                )
            )
        ),
        Story(
            id = "sleepy_bear",
            title = "The Sleepy Starlight Bear",
            subtitle = "A soothing, gentle bedtime story for calm and sweet dreams.",
            category = StoryCategory.BEDTIME,
            readingTimeMinutes = 4,
            xpReward = 15,
            starsReward = 4,
            coverEmoji = "🐻",
            primaryColorHex = 0xFF1E293B,
            isFeatured = false,
            isBedtime = true,
            moral = "Rest brings peaceful thoughts and tomorrow's bright energy.",
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "Dusk in the Pine Woods",
                    paragraphs = listOf(
                        "The sun had dipped behind the lavender peaks of Whisper Mountain.",
                        "Little Barnaby Bear took a deep, slow breath of cool lavender air.",
                        "All across the forest, fireflies were turning on their soft golden nightlights.",
                        "The river hummed a slow, gentle lullaby against the smooth gray river pebbles."
                    ),
                    sceneEmoji = "🌙"
                ),
                StoryChapter(
                    chapterNumber = 2,
                    title = "A Blanket of Stars",
                    paragraphs = listOf(
                        "Mother Bear fluffed a nest of dry pine needles and soft green moss inside their warm cave.",
                        "'Look outside, Barnaby,' she whispered. 'The Big Dipper is smiling just for you.'",
                        "Barnaby curled his paws into a warm little ball and closed his heavy eyelids.",
                        "The stars flickered softly like gentle bedtime bells, watching over every dreaming creature in the woods."
                    ),
                    sceneEmoji = "⭐"
                )
            )
        ),
        Story(
            id = "superhero_spark",
            title = "Superhero Spark & the Runaway Robot",
            subtitle = "Speed through Neo City to help a bewildered robot find his way home.",
            category = StoryCategory.HEROES,
            readingTimeMinutes = 6,
            xpReward = 25,
            starsReward = 6,
            coverEmoji = "🦸",
            primaryColorHex = 0xFFEC4899,
            isFeatured = true,
            moral = "Even the biggest hero's greatest power is a gentle helping hand.",
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "Alarm at the Cyber Lab",
                    paragraphs = listOf(
                        "In the glowing neon metropolis of Tech City, a siren chimed like a playful synthesizer.",
                        "Spark, a superhero wearing lightning sneakers and a solar cape, sprang onto the rooftop.",
                        "A mini-robot named Beep-0 had gotten confused by a spinning carousel in Central Park!",
                        "Beep-0 was spinning around and around, shooting harmless confetti everywhere."
                    ),
                    sceneEmoji = "⚡"
                ),
                StoryChapter(
                    chapterNumber = 2,
                    title = "The Lightning Rescue",
                    paragraphs = listOf(
                        "Spark dashed down the skyscraper side like a gentle streak of purple lightning.",
                        "Instead of grabbing Beep-0 forcefully, Spark whistled a tune Beep-0's sensors loved.",
                        "Beep-0 paused, twitched its antenna, and beeped in harmony.",
                        "Together, they safely stepped off the carousel, high-fived the park ranger, and restored peace to Tech City."
                    ),
                    sceneEmoji = "🤖"
                )
            )
        ),
        Story(
            id = "detective_kids",
            title = "The Mystery of the Missing Museum Clock",
            subtitle = "Join Detective Maya and solve the clues in Old Town Museum!",
            category = StoryCategory.MYSTERY,
            readingTimeMinutes = 7,
            xpReward = 30,
            starsReward = 7,
            coverEmoji = "🔎",
            primaryColorHex = 0xFF5B21B6,
            isFeatured = false,
            moral = "Careful observation and honesty reveal the truth.",
            chapters = listOf(
                StoryChapter(
                    chapterNumber = 1,
                    title = "The Empty Pedestal",
                    paragraphs = listOf(
                        "Detective Maya adjusted her magnifying glass and kneeled near the empty pedestal in the Grand Gallery.",
                        "The antique pendulum clock, which played a delightful chime every noon, was completely gone!",
                        "Curator Higgins threw up his hands: 'The grand gala is in three hours! We need that chime!'",
                        "Maya pulled out her detective notebook. 'Don't worry, Mr. Higgins. Every clue tells a story.'"
                    ),
                    sceneEmoji = "🔍"
                ),
                StoryChapter(
                    chapterNumber = 2,
                    title = "Footprints in Sugar Powder",
                    paragraphs = listOf(
                        "Near the window, Maya found white powdery dust on the windowsill.",
                        "She dabbed a finger to inspect it: powdered sugar, not dust!",
                        "A small trail of tiny paw prints led from the windowsill directly toward the courtyard fountain.",
                        "And floating in the fountain was a shiny clock pendulum, being used as an anchor for a toy pirate boat!"
                    ),
                    sceneEmoji = "🐾"
                ),
                StoryChapter(
                    chapterNumber = 3,
                    title = "The Little Culprit",
                    paragraphs = listOf(
                        "Hiding behind the hydrangeas was Barnaby the bakery cat, licking frosting off his whiskers.",
                        "He hadn't stolen the clock for malice—he just wanted the shiny pendulum to play sailor!",
                        "Maya retrieved the clock carefully and helped Mr. Higgins polish the golden gears.",
                        "At twelve o'clock, the clock chimed beautifully, and Barnaby got a bowl of warm milk for being the town's cutest sailor."
                    ),
                    sceneEmoji = "🕰️"
                )
            )
        )
    )

    // Sequencing Games
    val sequencingGame = SequencingGame(
        id = "seq_hero_journey",
        storyTitle = "The Great Quest of Sir Oliver",
        description = "Can you put the story scenes into the correct order from beginning to end?",
        scenes = listOf(
            SequenceCard(1, 1, "The Ancient Map", "Hero finds an old glowing map hidden in an antique book.", "🗺️"),
            SequenceCard(2, 2, "Entering the Forest", "Hero walks beneath the whispering canopy of Enchanted Woods.", "🌲"),
            SequenceCard(3, 3, "The Crystal Castle", "Hero discovers the magnificent castle shining on the mountain.", "🏰"),
            SequenceCard(4, 4, "Solving the Mystery", "Hero unlocks the magical door using the three harmonic bells.", "🗝️"),
            SequenceCard(5, 5, "Returning in Glory", "Hero returns to the village where everyone celebrates with cheers!", "🎉")
        ),
        xpReward = 35,
        starsReward = 10
    )

    // Quizzes
    val storyQuizzes = listOf(
        StoryQuiz(
            id = "quiz_dragon_valley",
            storyId = "dragon_moonlight",
            title = "Moonlight Valley Quiz",
            subtitle = "Test your knowledge of Oliver and Pip the dragon!",
            category = "Fantasy",
            difficulty = "Easy",
            xpReward = 30,
            starsReward = 6,
            questions = listOf(
                QuizQuestion(
                    id = "q1",
                    question = "What was Oliver searching for at the beginning of the story?",
                    illustrationEmoji = "🏮",
                    options = listOf("His grandfather's lantern", "A bag of gold coins", "A hidden diamond", "A lost sheep"),
                    correctAnswerIndex = 0,
                    explanation = "Oliver went up the mountain looking for his grandfather's beloved lantern."
                ),
                QuizQuestion(
                    id = "q2",
                    question = "What happened to the ground where the dragon walked?",
                    illustrationEmoji = "🌸",
                    options = listOf("It turned into ice", "Tiny blue spark-flowers bloomed", "It left black soot", "It turned to gold"),
                    correctAnswerIndex = 1,
                    explanation = "Pip's magical paws made blue spark-flowers bloom wherever he stepped!"
                ),
                QuizQuestion(
                    id = "q3",
                    question = "What was the dragon's name?",
                    illustrationEmoji = "🐉",
                    options = listOf("Barnaby", "Pip", "Draco", "Sparky"),
                    correctAnswerIndex = 1,
                    explanation = "The sweet baby dragon was named Pip."
                ),
                QuizQuestion(
                    id = "q4",
                    question = "What treat did Oliver share with Pip?",
                    illustrationEmoji = "🍎",
                    options = listOf("Hot soup", "Sweet dried apples", "Fresh berries", "Honey bread"),
                    correctAnswerIndex = 1,
                    explanation = "Oliver shared sweet dried apples from his traveler pack."
                ),
                QuizQuestion(
                    id = "q5",
                    question = "What is the moral of the story?",
                    illustrationEmoji = "💖",
                    options = listOf("Always run away from dragons", "Kindness and courage can melt fear", "Never share snacks", "Stay inside at night"),
                    correctAnswerIndex = 1,
                    explanation = "Kindness and courage brought Oliver and Pip together as lifelong friends."
                )
            )
        ),
        StoryQuiz(
            id = "quiz_quick_fairytales",
            title = "Fairy Tale Wonders",
            subtitle = "10 quick questions about magical worlds and classic stories!",
            category = "Fairy Tales",
            difficulty = "Quick Quiz",
            xpReward = 25,
            starsReward = 5,
            questions = listOf(
                QuizQuestion(
                    id = "q_ft_1",
                    question = "Where do fairies usually sleep in magical stories?",
                    illustrationEmoji = "🌸",
                    options = listOf("Inside hollow flowers", "On top of clock towers", "Under ocean rocks", "Inside metal boxes"),
                    correctAnswerIndex = 0,
                    explanation = "Fairies love nesting in sweet-smelling flower blossoms!"
                ),
                QuizQuestion(
                    id = "q_ft_2",
                    question = "What tool does an explorer use to find direction?",
                    illustrationEmoji = "🧭",
                    options = listOf("A paintbrush", "A compass", "A flute", "A mirror"),
                    correctAnswerIndex = 1,
                    explanation = "A compass needle points north to help explorers stay on course."
                ),
                QuizQuestion(
                    id = "q_ft_3",
                    question = "What mythical creature has colorful feathers and can rise again?",
                    illustrationEmoji = "🔥",
                    options = listOf("A Phoenix", "A Griffin", "A Mermaid", "A Pegasus"),
                    correctAnswerIndex = 0,
                    explanation = "A Phoenix is a legendary bird of light and rebirth."
                ),
                QuizQuestion(
                    id = "q_ft_4",
                    question = "What character solves mysteries by looking for clues?",
                    illustrationEmoji = "🔍",
                    options = listOf("A Detective", "A Blacksmith", "A Baker", "A Gardener"),
                    correctAnswerIndex = 0,
                    explanation = "Detectives use keen observation and logic to solve puzzles."
                )
            )
        )
    )

    // Achievements
    val allAchievements = listOf(
        Achievement("first_story", "First Story", "Completed reading your very first storybook!", "📖", 20, "Reading"),
        Achievement("book_explorer", "Book Explorer", "Read 3 different magical stories in StoryLand.", "🧭", 30, "Reading"),
        Achievement("creative_writer", "Creative Writer", "Built a custom fairy tale with the Story Builder!", "✍️", 40, "Creativity"),
        Achievement("mystery_solver", "Mystery Solver", "Found all the clues and solved a Detective case.", "🔍", 35, "Mystery"),
        Achievement("story_maker", "Story Maker", "Created and customized your own unique character.", "🎨", 30, "Creativity"),
        Achievement("dragon_friend", "Dragon Friend", "Completed the Dragon Rescue interactive challenge.", "🐉", 50, "Adventure"),
        Achievement("story_sequencer", "Story Sequencer", "Arranged story cards in perfect chronological order.", "🧩", 25, "Learning"),
        Achievement("quiz_master", "Quiz Master", "Scored 100% on any story comprehension quiz.", "🏆", 40, "Quiz"),
        Achievement("fantasy_explorer", "Fantasy Explorer", "Explored all magical locations in the Magic Kingdom.", "🏰", 45, "Adventure"),
        Achievement("voice_star", "Narration Star", "Recorded your own voice reading a story.", "🎙️", 35, "Voice"),
        Achievement("streak_champ", "Streak Champion", "Maintained a 5-day reading streak!", "🔥", 50, "Streak"),
        Achievement("story_master", "Story Master", "Reached 1,000 XP and earned the highest crown rank!", "👑", 100, "Mastery")
    )

    // Collectible Characters
    val collectibleCharacters = listOf(
        CharacterItem("char_pip", "Pip the Dragon", "Baby Guardian", "Moonlight Valley", "🐉", "Starlight Breath", "A playful emerald dragon who loves warm honey apples.", true),
        CharacterItem("char_oliver", "Sir Oliver", "Courageous Explorer", "Moonlight Valley", "🧭", "Compass of Hope", "A brave boy with a big heart who never gives up.", true),
        CharacterItem("char_queen_glimmer", "Queen Glimmer", "Fairy Monarch", "Enchanted Forest", "🧚", "Sparkle Shield", "Ruler of Whisper Brook whose wings scatter starlight.", true),
        CharacterItem("char_barnaby", "Captain Barnaby", "Pirate Navigator", "Pirate Seas", "🏴‍☠️", "Wave Whispering", "Loves maps and sails wherever the wind smells of adventure.", false),
        CharacterItem("char_maya", "Detective Maya", "Master Sleuth", "Mystery Island", "🔍", "Keen Observation", "Not a single paw print or riddle escapes her keen eyes.", false),
        CharacterItem("char_spark", "Hero Spark", "City Guardian", "Hero City", "🦸", "Lightning Dash", "Protects Tech City with lightning speed and kindness.", false),
        CharacterItem("char_beep0", "Beep-0", "Friendly Automaton", "Robot City", "🤖", "Gyro Balance", "A helpful little robot who loves playing festive tunes.", false),
        CharacterItem("char_sir_alden", "Sir Alden", "Silver Knight", "Magic Kingdom", "🛡️", "Honor Shield", "Stands guard at the Castle gates with a smile for every traveler.", false)
    )

    // Worlds
    val fantasyWorlds = listOf(
        WorldItem(
            id = "magic_kingdom",
            name = "Magic Kingdom",
            emoji = "🏰",
            description = "A sunlit realm of towering spires, rainbow rivers, and ancient spellcraft.",
            themeColorHex = 0xFF7C3AED,
            isUnlocked = true,
            locations = listOf(
                WorldLocation("loc_castle", "Sun-Gem Castle", "🏰", "The high throne room overlooking golden meadows.", true),
                WorldLocation("loc_forest", "Enchanted Forest", "🌲", "Luminous moss and speaking fireflies.", true),
                WorldLocation("loc_wizard_tower", "Wizard's Tower", "🧙", "Spiraling library containing millions of star charts.", false),
                WorldLocation("loc_crystal_cave", "Crystal Caves", "💎", "Glittering caverns humming with musical stones.", false)
            )
        ),
        WorldItem(
            id = "dragon_valley",
            name = "Dragon Valley",
            emoji = "🐉",
            description = "A peaceful mountainous sanctuary where dragons and travelers live as friends.",
            themeColorHex = 0xFF0D9488,
            isUnlocked = true,
            locations = listOf(
                WorldLocation("loc_whispering_glen", "Whispering Glen", "🏞️", "Quiet waterfalls cascading into silver pools.", true),
                WorldLocation("loc_star_bridge", "Star Chasm Bridge", "🌉", "A natural stone arch under the starry heavens.", true),
                WorldLocation("loc_dragon_roost", "Dragon Roost", "⛰️", "The highest warm thermal ledges above the clouds.", false)
            )
        ),
        WorldItem(
            id = "pirate_seas",
            name = "Pirate Seas",
            emoji = "⛵",
            description = "Warm turquoise waves dotted with tropical atolls and hidden coves.",
            themeColorHex = 0xFF0284C7,
            isUnlocked = false,
            locations = listOf(
                WorldLocation("loc_treasure_isle", "Treasure Island", "🏝️", "Swaying coconut palms and buried sea chests.", false),
                WorldLocation("loc_ancient_lighthouse", "Ancient Lighthouse", "🏮", "A beacon that shines colored starlight across storms.", false)
            )
        ),
        WorldItem(
            id = "mystery_island",
            name = "Mystery Island",
            emoji = "🔎",
            description = "An uncharted misty sanctuary where secrets and forgotten ruins wait to be unraveled.",
            themeColorHex = 0xFFB45309,
            isUnlocked = false,
            locations = listOf(
                WorldLocation("loc_mansion", "Abandoned Observatory", "🔭", "Old brass telescopes pointed at mysterious constellations.", false),
                WorldLocation("loc_jungle", "Whispering Jungle", "🌴", "Vines that echo playful riddles in the wind.", false)
            )
        )
    )

    // Reading Challenges
    val dailyChallenges = listOf(
        ReadingChallenge("c1", "Read 1 Story Today", "Enjoy an adventure from the library", "📖", 20, 5, 1, 1, true),
        ReadingChallenge("c2", "Complete 1 Story Quiz", "Test your comprehension skills", "🏆", 25, 5, 0, 1, false),
        ReadingChallenge("c3", "Create a Fairy Tale", "Use the Fairy Tale Builder", "🎨", 30, 8, 1, 1, true),
        ReadingChallenge("c4", "Listen with Read Along", "Follow narrated story sentences", "🎧", 20, 5, 0, 1, false)
    )

    // Detective Case Data
    val detectiveCase = DetectiveCase(
        id = "case_golden_goblet",
        title = "The Mystery of the Missing Royal Goblet",
        mysteryDescription = "The golden coronation goblet vanished from the castle banquet hall during the masquerade ball! Can you collect all 4 clues and deduce who took it?",
        clues = listOf(
            DetectiveClue("clue_feather", "A Shimmering Blue Feather", "Found near the banquet window curtains.", "🪶", "Banquet Hall Window", true),
            DetectiveClue("clue_breadcrumbs", "Sweet Blueberry Crumb Trail", "Leads away from the pastry table toward the garden patio.", "🫐", "Pastry Table", true),
            DetectiveClue("clue_footprint", "Small Webbed Footprint", "Imprinted in soft mud beside the garden lily pond.", "🐾", "Lily Pond Garden", false),
            DetectiveClue("clue_ribbon", "Silken Sailor Knot", "Left caught on the low rose bush branch.", "🎀", "Rose Bush", false)
        ),
        suspects = listOf(
            "Sir Archibald (The Royal Knight with steel boots)",
            "Barnaby the Blue-Footed Duck (Loves shiny cups & berries)",
            "Madame Celeste (The Wizard wearing long velvet robes)",
            "Gideon the Butler (Never leaves the pantry kitchen)"
        ),
        correctCulpritIndex = 1,
        solutionExplanation = "The blue feathers, blueberry crumbs, webbed footprints, and sailor knot all point to Barnaby the sailor duck! He thought the goblet was a shiny new birdbath."
    )
}
