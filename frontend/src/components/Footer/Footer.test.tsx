import React from 'react'
import renderer from 'react-test-renderer'
import { Footer } from './Footer'

test('it renders correctly', () => {
  const root = renderer.create(<Footer />).toJSON()
  expect(root).toMatchSnapshot()
})
