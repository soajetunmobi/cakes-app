import React, { FunctionComponent, PropsWithChildren, ReactNode } from 'react'
import { Header } from '../Header'
import { Main } from '../Main'
import { Footer } from '../Footer'

export const PageLayout: FunctionComponent = ({ children }: PropsWithChildren<ReactNode>) => {
  return (
    <>
      <Header />
      <Main>{children}</Main>
      <Footer />
    </>
  )
}
