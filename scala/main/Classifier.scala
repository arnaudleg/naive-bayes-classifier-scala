package scala.main

import collection.mutable.HashMap

/**
 * Naive Bayes Classifier.
 */
class Classifier(lang: String) {
  def this() = this("english")

  private val _stemming     = lang
  private var _wcount       = HashMap[Pair[String, String], Int]()    // [[category, word], count]
  private var _ccount       = HashMap[String, Int]()                  // [category, count]
  private var _probs        = HashMap[String, Double]()               // [category, probability]

  def wcount = _wcount
  def ccount = _ccount

  def train(category: String, text: String) {
    val tokenizer = new Tokenizer(text, _stemming)
    tokenizer.eachWord().foreach(word => _wcount.put((category, word), 1+_wcount.get((category, word)).getOrElse(0)))
    _ccount.put(category, 1+_ccount.get(category).getOrElse(0))
  }

  private def wordProb(word: String,  cat: String): Double = {
    if(!_wcount.contains((cat,word)))
      return 0.0
    _wcount((cat,word)) / _ccount(cat).floatValue()
  }

  private def wordWeightedAverage(word: String, cat: String): Double = {
    val weight = 1
    val assumed_prob = 0.5
    // current probability
    val basic_prob = wordProb(word, cat)
    // count the number of times this word has appeared in all categories
    var totals = 0
    _ccount foreach (c =>
      if(_wcount.contains((c._1, word)))
        totals += _wcount((c._1, word))
    )
    // final weighted average
    (weight * assumed_prob + totals * basic_prob) / (weight + totals)
  }

  private def totalCountCat(): Int = {
    _ccount.foldLeft(0)(_+_._2)
  }

  private def docProb(text: String, cat: String): Double = {
    var prob = 1.0
    val tokenizer = new Tokenizer(text, _stemming)
    tokenizer.eachWord().foreach(word =>
      prob *= wordWeightedAverage (word, cat)
    )
    prob
  }

  private def textProb(text: String, cat: String): Double = {
    val catProb = _ccount(cat) / totalCountCat().floatValue()
    val wProb = docProb(text, cat)
    catProb * wProb
  }

  private def catScores(text: String) {
    _ccount foreach (c => _probs.put(c._1, textProb(text, c._1)))
  }

  def classify(text: String): String = {
    catScores(text)
    _probs.maxBy(_._2)._1
  }
}


