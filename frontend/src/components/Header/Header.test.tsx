import React from 'react'
import renderer from 'react-test-renderer'
import { Header } from './Header'

test('it renders correctly', () => {
  const root = renderer.create(<Header />).toJSON()
  expect(root).toMatchSnapshot()
})
