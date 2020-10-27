import React, { FunctionComponent } from 'react'

export const Footer: FunctionComponent = () => {
  const currentYear = new Date().getFullYear()

  return (
    <footer className='footer'>
      <span>Cake Reviews</span>
      <br />
      {currentYear}
    </footer>
  )
}
