package scala.test

import org.scalatest.FunSuite
import main.Tokenizer

class TokenizerTest extends FunSuite {
  
  test("eachWord should return words without stopping words.") {
    val t = new Tokenizer("Hello world! How are you?", null)
    assert(t.eachWord() === List("hello", "world"))
  }

  test("eachWord should return words without stopping words and with stemming.") {
    val t = new Tokenizer("Lots of dogs, lots of cats! This is the information highway", "english")
    assert(t.eachWord() === List("lot", "dog", "lot", "cat", "inform", "highway"))
  }

  test("eachWord should return words for complicated tokens.") {
    val t = new Tokenizer("I don't really get what you want to accomplish. " +
      "There is a class TestEval2, you can do test_eval2 = TestEval2.new afterwards. " +
      "And: class A ... end always yields nil, so your output is ok I guess ;-)", "english")
    assert(t.eachWord() === List("realli", "want", "accomplish", "class",
          "testev", "test", "eval", "testev", "new", "class", "end",
          "yield", "nil", "output", "ok", "guess"))
  }

}
