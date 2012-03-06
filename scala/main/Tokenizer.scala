package scala.main

/**
 * <p>Extract words from a given text <code>t</code> after removing <code>stopWords</code>.</p>
 * Keep duplicate words.
 * Able to stem the words in a given language <code>l</code> before returning them.
 * @param t some text
 * @param l language for stemming. if null, no stemming
 */
class Tokenizer(t: String, l: String) {
  private val _text = t
  private val _lang = l

  private def words(text: String): Array[String] = text.toLowerCase.
    replaceAll("""['`]""", "").replaceAll("""[^a-zA-Z]+""", " ") split ("""\s+""")

  def eachWord(text: String, lang:String): List[String] = {
    var stemLang: String = null
    if (lang != null) stemLang = lang.toLowerCase
    if (stemLang != null && !(Tokenizer.languages contains stemLang))
      sys.error("The stemming language specified is not available.")
    var tokens = List[String]()
    if (text == "") return tokens
    if (stemLang != null) {
      val stem = Class.forName(Tokenizer.stemPackage + stemLang + Tokenizer.stemFile).newInstance.asInstanceOf[{
        def setCurrent(name: String)
        def stem()
        def getCurrent: String
      }]
      words(text).foreach((word: String) =>
        if (!(Tokenizer.stopWords contains word)) {
          stem.setCurrent(word)
          stem.stem()
          tokens = tokens ::: List(stem.getCurrent)
        }
      )
    } else {
      words(text).foreach((word: String) =>
        if (!(Tokenizer.stopWords contains word)) {
          tokens = tokens ::: List(word)
        }
      )
    }
    tokens
  }

  def eachWord(text: String): List[String] = {
    eachWord(text, _lang)
  }

  def eachWord(): List[String] = {
    eachWord(_text)
  }
}

object Tokenizer {
  val languages = Array[String]("english", "french", "danish", "dutch", "finnish", "german",
    "hungarian", "italian", "norwegian", "porter", "portuguese", "romanian",
    "russian", "spanish", "swedish", "turkish")
  val stemPackage = "lib.snowball.ext."
  val stemFile = "Stemmer"
  var stopWords = Array[String]("a", "about", "above", "across", "after", "afterwards",
    "again", "against", "all", "almost", "alone", "along",
    "already", "also", "although", "always", "am", "among",
    "amongst", "amoungst", "amount", "an", "and", "another",
    "any", "anyhow", "anyone", "anything", "anyway", "anywhere",
    "are", "around", "as", "at", "back", "be",
    "became", "because", "become", "becomes", "becoming", "been",
    "before", "beforehand", "behind", "being", "below", "beside",
    "besides", "between", "beyond", "bill", "both", "bottom",
    "but", "by", "call", "can", "cannot", "cant", "dont",
    "co", "computer", "con", "could", "couldnt", "cry",
    "de", "describe", "detail", "do", "done", "down",
    "due", "during", "each", "eg", "eight", "either",
    "eleven", "else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every",
    "everyone", "everything", "everywhere", "except", "few", "fifteen",
    "fify", "fill", "find", "fire", "first", "five",
    "for", "former", "formerly", "forty", "found", "four",
    "from", "front", "full", "further", "get", "give",
    "go", "had", "has", "hasnt", "have", "he",
    "hence", "her", "here", "hereafter", "hereby", "herein",
    "hereupon", "hers", "herself", "him", "himself", "his",
    "how", "however", "hundred", "i", "ie", "if",
    "in", "inc", "indeed", "interest", "into", "is",
    "it", "its", "itself", "keep", "last", "latter",
    "latterly", "least", "less", "ltd", "made", "many",
    "may", "me", "meanwhile", "might", "mill", "mine",
    "more", "moreover", "most", "mostly", "move", "much",
    "must", "my", "myself", "name", "namely", "neither",
    "never", "nevertheless", "next", "nine", "no", "nobody",
    "none", "noone", "nor", "not", "nothing", "now",
    "nowhere", "of", "off", "often", "on", "once",
    "one", "only", "onto", "or", "other", "others",
    "otherwise", "our", "ours", "ourselves", "out", "over",
    "own", "part", "per", "perhaps", "please", "put",
    "rather", "re", "same", "see", "seem", "seemed",
    "seeming", "seems", "serious", "several", "she", "should",
    "show", "side", "since", "sincere", "six", "sixty",
    "so", "some", "somehow", "someone", "something", "sometime",
    "sometimes", "somewhere", "still", "such", "system", "take",
    "ten", "than", "that", "the", "their", "them",
    "themselves", "then", "thence", "there", "thereafter", "thereby",
    "therefore", "therein", "thereupon", "these", "they", "thick",
    "thin", "third", "this", "those", "though", "three",
    "through", "throughout", "thru", "thus", "to", "together",
    "too", "top", "toward", "towards", "twelve", "twenty",
    "two", "un", "under", "until", "up", "upon",
    "us", "very", "via", "was", "we", "well",
    "were", "what", "whatever", "when", "whence", "whenever",
    "where", "whereafter", "whereas", "whereby", "wherein", "whereupon",
    "wherever", "whether", "which", "while", "whither", "who",
    "whoever", "whole", "whom", "whose", "why", "will",
    "with", "within", "without", "would", "yet", "you", "your", "yours",
    "yourself", "yourselves")
}


