import React, { FunctionComponent, PropsWithChildren, ReactNode } from 'react'

export const ErrorPanel: FunctionComponent = ({ children }: PropsWithChildren<ReactNode>) => {
  return <div className='error-container'>{children}</div>
}
