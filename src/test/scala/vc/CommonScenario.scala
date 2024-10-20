package vc

import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import vc.Feeders._

object CommonScenario{
  def apply(): ScenarioBuilder = new CommonScenario().scn
}

class CommonScenario {

  val loginGroup: ChainBuilder = group("my login"){
    exec(Actions.login)
      .exec(Actions.me)
  }

  val scn: ScenarioBuilder = scenario("Common scenario")
    .feed(myFeed)
    .feed(users)
    .exec(Actions.getMainPage)
    .pause(1,3)
    .randomSwitch(
      20.0 -> exec(Actions.getPopular),
      80.0 -> exec(Actions.getNew)
    )
    .exec(Actions.getCourses)
    .exec(Actions.discovery)
    .exec(loginGroup)

}
