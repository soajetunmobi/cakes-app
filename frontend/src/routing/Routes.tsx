import React, { FunctionComponent } from 'react'
import { Switch, Route } from 'react-router-dom'
import { routes } from '../common/const/routes'
import { HomePage } from '../pages/HomePage'
import { ViewReviewPage } from '../pages/ViewReviewPage'
import { AddReviewPage } from '../pages/AddReviewPage'

const Routes: FunctionComponent = () => {
  return (
    <Switch>
      <Route exact path={routes.cakes} component={HomePage} />
      <Route exact path={routes.review} component={ViewReviewPage} />
      <Route exact path={routes.add} component={AddReviewPage} />
      <Route path='/' exact component={HomePage} />
    </Switch>
  )
}

export default Routes
