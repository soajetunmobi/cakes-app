import React from 'react'
import renderer from 'react-test-renderer'
import { Main } from './Main'

test('it renders correctly', () => {
  const root = renderer.create(<Main />).toJSON()
  expect(root).toMatchSnapshot()
})
