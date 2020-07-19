package global.msnthrp.langen.models


const val VOWELS = "jaeiouyōïīíūôœə"
const val CONSONANTS = "bdvzgwptskcflmnrhšč\$φþģņĸ"

const val MINIMAL = "abdefgiklmnoprstuvz"
const val MAXIMAL = VOWELS + CONSONANTS

val COMMON_REPLACES = mapOf(
    "i'm" to "i am",
    "you're" to "you are",
    "she's" to "she is",
    "it's" to "it is",
    "isn't" to "is not",
    "aren't" to "are not",
    " am " to " be ",
    " are " to " be ",
    " is " to " be "
)

val LONG_WORD_REPLACES = mapOf(
    " of " to "o",
    " the " to " tre",
    " a " to " an",
    " an " to " an",
    "-" to "",
    "'" to ""
)

val SHORT_WORD_REPLACES = mapOf(
    " of " to " o ",
    " the " to " ",
    " a " to " ",
    " an " to " ",
    "-" to " ",
    "\'" to " "
)

val WIDEN_MAP = mapOf(
    "sh" to "š",
    "ch" to "č",
    "sch" to "$",
    "ph" to "ﬀ",
    "th" to "þ",
    "gh" to "ģ",
    "qu" to "kv",
    "x" to "ks",
    "oo" to "ō",
    "ea" to "ï",
    "ee" to "ī",
    "ie" to "í",
    "ou" to "ū",
    "au" to "ô",
    "ur" to "œ",
    "er" to "ə",
    "ng" to "ņ",
    "ck" to "ĸ"
)

val FREQUENT_GROUPS = listOf(
    "ly", "iņ", "st", "nt",
    "in", "un", "at", "not",
    "do", "ģt", "re", "ion",
    "age", "nce"
)

val LONG_WORD_GROUPS = listOf(
    "mente", "inger", "zer",
    "kkan", "esta", "para",
    "pero", "kime"
)

val SHORT_WORD_GROUPS = listOf(
    "k", "t", "z", "s",
    "n", "r", "a", "i", "u",
    "ī", "e", "œ", "a", "o"
)

val BOTTLE_NECK = mapOf(
    "j" to "i",
    "y" to "i",
    "ō" to "o",
    "ï" to "i",
    "ī" to "i",
    "í" to "i",
    "ū" to "u",
    "ô" to "o",
    "œ" to "e",
    "ə" to "e",
    "w" to "v",
    "c" to "k",
    "h" to "",
    "š" to "s",
    "č" to "s",
    "$" to "s",
    "φ" to "f",
    "þ" to "t",
    "ģ" to "g",
    "ņ" to "n",
    "ĸ" to "k"
)

const val SAMPLE_TEXT = "People think that a liar gains a victory over his victim. What I've learned " +
        "is that a lie is an act of self-abdication, because one surrenders one's reality " +
        "to the person to whom one lies, making that person one's master, condemning oneself " +
        "from then on to faking the sort of reality that person's view requires to be faked…" +
        "The man who lies to the world, is the world's slave from then on…There are no white lies, " +
        "there is only the blackest of destruction, and a white lie is the blackest of all."

const val LANGUAGE = "language"

val LANGUAGE_SYNONYMS = listOf(
    LANGUAGE,
    "tongue",
    "speech",
    "lingo",
    "dialect"
)

const val LARGE_TEXT = """At three o'clock in the morning, even a large city is quiet and dark
and almost dead. At times, the city twitches in its sleep; occasionally
it rolls over or mutters to itself. But only rarely is its slumber
shattered by a scream....

"Johnny! Hey, Johnny!" cries Chester McRae, his eyes as dull and
poisonous as two tiny toads.

"Let's make it, man ... let's split...." whispers Bartholomew Oliver,
one finger brushing his nose like a rattler nosing a dead mouse.

"I don make no move without my boys," says Oswald Williams, his hands
curled like scorpion tails.

Together they walk down the street, moving with slow insolence,
their lips curled in snarls or slack with indifference, their eyes
glittering with hidden hatreds. But they are not alone in the city.
The college boys are coming, in their dirty jeans and beer-stained
tee-shirts; so too are the lawyers, in dusty jackets and leather pants;
so come the doctors and the businessmen, on stolen motorcycles; the
bricklayers and gas station attendants, the beatniks and dope pushers,
the bankers and lifesaving instructors, the butchers, the bakers, the
candlestick makers... they are all coming, flocking into the city for
reasons not their own, wandering in twos and threes and twenties, all
of them sullen and quiet, all of them shuffling beneath darkly-hued
clouds of ill intent, all of them proud and deadly and virile, filling
the streets by the thousands now, turning the streets into rivers of
flesh....

"Hey, Johnny," says Chester, "let's cool this dump."

"Man, let's make it with the skirts," says Bartholomew.

"I don see no skirts," says Chester.

"You pig," snarls Ozzie.

The mob is monstrous now, like a pride of lion cubs, beyond count in
their number, without equal in their leonine strength, above the common
quick in their immortal pride, milling through the hot black veldt,
swarming in the city streets. Millions of them, more than the eye can
see or the mind can bear. It seems that no man sleeps, that every male
in the great city must walk tonight.

"Johnny," says Chester, "I don dig no chicks on the turf."

"Eeee, colay. What a drag," whispers Bartholomew.

"You goddam logical positivist," snarls Ozzie.

       *       *       *       *       *

An uneasy sound ripples through the mob, like the angry hiss of an
injured ego, moving from street to street and swelling upward in a
sudden, angry roar ... they want their women, the dance-hall girls,
the young waitresses, the nowhere chicks in five dollar dresses,
the Spanish girls with eyes as dark as the Spanish night. And then,
as though by accident, one man looks up at the starry sky and sees
_her_--sees her standing on a balcony far above them, twenty stories
above them, up where the wind can blow her hair and billow her blue
dress like an orchid of the night.

She laughs gently, without fear, gazing down at the mindless mob of
rebels.

They laugh too, just as gently, their quiet eyes crawling over the
sight of her body, far above.

"Thass my chick," whispers Chester.

"Cool it, daddy," says Bartholomew, slipping into a pair of dark
glasses and touching his lips with the tip of his tongue. "That skirt
is private property."

"You boys may walk and talk," says Ozzie, "but you don play. You don
play with Rio's girl."

Suddenly, angry words and clenched fists erupt from the proud, quiet
millions that flood the streets. Suddenly, a roar like the roar of
lions rises up and buffets the girl in blue, the girl on the balcony.
She laughs again, for she knows that they are fighting for her.

A figure appears on the balcony, next to the girl. The figure is a man,
and he too is dressed in blue. Suddenly, just as suddenly as it began,
the fighting ceases.

"My God," whispers Chester, his cheeks gone pale, "what am I doing out
here?"

"Maybe I got the D.T.s," whispers Bartholomew, "but maybe I don't...."
He sits down on the curb and rubs his head in disbelief.

Oswald does not speak. His shame is the greatest. He slinks into the
darkness of an alley and briefly wishes for an overcoat.

The pride of lion cubs has been routed, and now they scatter, each one
scrambling for his private den of security, each one lost in a wild and
nameless fear. In twos and threes and twenties they rush back to their
homes, their wives, their endless lives.

Far above, in the apartment with the balcony, a man in blue is chiding
a girl in blue.

"That was scarcely reasonable, Dorothy."

"But Daddy, you promised to let me have them for the entire night!"

"Yes, but...."

"I wasn't really going to let them hurt themselves! Really, I wasn't!"

"But, Dorothy--you know these things can get out of hand."

"Oh, but Daddy, you know how I adore strong, quiet, proud men.
Rebellious men like Marlon."

"Yes, and you know how _I_ adore order and peace. There shall be _no_
more riots! And tomorrow our little puppets shall go back to their
'dull' lives, as you so wittily put it, and everything shall be as I
wish."""""