package edu.luc.cs.webservices.unfiltered.bookmarks

import unfiltered.request._
import unfiltered.response._
import unfiltered.jetty.Server

import org.clapper.avsl.Logger

/** embedded server */
object Main {
  val logger = Logger(Main getClass)
  val repository = new ThreadSafeBookmarksRepository(new InMemoryBookmarksRepository)
  val plans = Seq(rootPlan, 
                  new UserPlan(repository , userRenderer),
                  new BookmarksPlan(repository),
                  new BookmarkPlan(repository))
  def applyPlans = plans.foldLeft(_: Server)(_ filter _)
  
  def main(args: Array[String]) {
    logger.info("starting unfiltered app at localhost on port %s" format 8080)
    applyPlans(unfiltered.jetty.Http(8080)).run() 
  }
}
