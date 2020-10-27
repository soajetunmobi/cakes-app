import React, { FunctionComponent, PropsWithChildren, ReactNode } from 'react'

export const Main: FunctionComponent = ({ children }: PropsWithChildren<ReactNode>) => {
  return <main className='content'>{children}</main>
}
