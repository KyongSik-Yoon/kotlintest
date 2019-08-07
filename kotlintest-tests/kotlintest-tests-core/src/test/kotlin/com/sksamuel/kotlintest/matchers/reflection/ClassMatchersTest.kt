package com.sksamuel.kotlintest.matchers.reflection

import com.sksamuel.kotlintest.matchers.reflection.annotations.Fancy
import com.sksamuel.kotlintest.matchers.reflection.classes.FancyItem
import com.sksamuel.kotlintest.matchers.reflection.classes.SimpleItem
import io.kotlintest.matchers.reflection.*
import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec
import java.io.FileNotFoundException
import java.io.IOException
import kotlin.reflect.KVisibility

class ClassMatchersTest : FreeSpec() {
  init {
    "should" - {
      "have annotations" {
        FancyItem::class.shouldHaveAnnotations()
        FancyItem::class shouldHaveAnnotations 1
        SimpleItem::class shouldHaveAnnotations 0
      }
      "have annotation" {
        FancyItem::class.shouldBeAnnotatedWith<Fancy>()
      }
      "have annotation with lambda" {
        FancyItem::class.shouldBeAnnotatedWith<Fancy> {
          it.cost shouldBe 500
        }
      }
      "have function" {
        FancyItem::class shouldHaveFunction "fancyFunction"
        SimpleItem::class shouldHaveFunction "simpleFunction"
      }
      "have function with lambda" {
        FancyItem::class.shouldHaveFunction("fancyFunction") {
          it.returnType.shouldBeOfType<Int>()
        }
        FancyItem::class.shouldHaveFunction("fancyFunctionWithString") {
          it.returnType.shouldBeOfType<String>()
        }
        SimpleItem::class.shouldHaveFunction("simpleFunction") {
          it.returnType.shouldBeOfType<Int>()
        }
      }
      "have member property" {
        FancyItem::class.shouldHaveMemberProperty("name")
        FancyItem::class.shouldHaveMemberProperty("value")
        FancyItem::class.shouldHaveMemberProperty("otherField")
      }
      "have member property with lambda" {
        FancyItem::class.shouldHaveMemberProperty("name") {
          it.returnType.shouldBeOfType<String>()
        }
        FancyItem::class.shouldHaveMemberProperty("value") {
          it.returnType.shouldBeOfType<Int>()
        }
        FancyItem::class.shouldHaveMemberProperty("otherField") {
          it.returnType.shouldBeOfType<Long>()
        }
      }
      "be subtype of" {
        IOException::class.shouldBeSubtypeOf<Exception>()
        FileNotFoundException::class.shouldBeSubtypeOf<IOException>()
        FileNotFoundException::class.shouldBeSubtypeOf<Exception>()
      }
      "be supertype of" {
        Exception::class.shouldBeSupertypeOf<IOException>()
        Exception::class.shouldBeSupertypeOf<FileNotFoundException>()
        IOException::class.shouldBeSupertypeOf<FileNotFoundException>()
      }
      "be data" {
        FancyItem.FancyData::class.shouldBeData()
      }
      "be sealed" {
        SimpleItem.Action::class.shouldBeSealed()
      }
      "be companion" {
        SimpleItem.Companion::class.shouldBeCompanion()
      }
      "have a primary constructor" {
        SimpleItem::class.shouldHavePrimaryConstructor()
      }
      "have visibility" {
        SimpleItem::class.shouldHaveVisibility(KVisibility.PUBLIC)
      }
    }
    "should not" - {
      "have annotations" {
        SimpleItem::class.shouldNotHaveAnnotations()
        FancyItem::class shouldNotHaveAnnotations 0
        FancyItem::class shouldNotHaveAnnotations 2
      }
      "have annotation" {
        SimpleItem::class.shouldNotBeAnnotatedWith<Fancy>()
      }
      "have function" {
        FancyItem::class shouldNotHaveFunction "foo"
        FancyItem::class shouldNotHaveFunction "bar"
        FancyItem::class shouldNotHaveFunction "simpleFunction"
        SimpleItem::class shouldNotHaveFunction "foo"
        SimpleItem::class shouldNotHaveFunction "bar"
        SimpleItem::class shouldNotHaveFunction "fancyFunction"
      }
      "have member property" {
        SimpleItem::class.shouldNotHaveMemberProperty("name")
        SimpleItem::class.shouldNotHaveMemberProperty("value")
      }
      "be subtype of" {
        Exception::class.shouldNotBeSubtypeOf<IOException>()
        IOException::class.shouldNotBeSubtypeOf<FileNotFoundException>()
      }
      "be supertype of" {
        IOException::class.shouldNotBeSupertypeOf<Exception>()
        FileNotFoundException::class.shouldNotBeSupertypeOf<Exception>()
        FileNotFoundException::class.shouldNotBeSupertypeOf<IOException>()
      }
      "be data" {
        FancyItem::class.shouldNotBeData()
      }
      "be sealed" {
        SimpleItem.Action.Action1::class.shouldNotBeSealed()
      }
      "be companion" {
        SimpleItem::class.shouldNotBeCompanion()
      }
      "have a primary constructor" {
        SimpleItem.Action.Action1::class.shouldNotHavePrimaryConstructor()
      }
      "have visibility" {
        SimpleItem::class.shouldNotHaveVisibility(KVisibility.PRIVATE)
        SimpleItem::class.shouldNotHaveVisibility(KVisibility.PROTECTED)
        SimpleItem::class.shouldNotHaveVisibility(KVisibility.INTERNAL)
      }
    }
  }
}