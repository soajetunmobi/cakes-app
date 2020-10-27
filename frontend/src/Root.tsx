import React, { FunctionComponent } from 'react'
import { BrowserRouter } from 'react-router-dom'
import { PageLayout } from './components/PageLayout'
import Routes from './routing/Routes'

const Root: FunctionComponent = () => {
  return (
    <BrowserRouter>
      <PageLayout>
        <Routes />
      </PageLayout>
    </BrowserRouter>
  )
}

export default Root
