import React from 'react'
import renderer from 'react-test-renderer'
import { LoadingIndicator } from './LoadingIndicator'

test('it renders correctly', () => {
  const root = renderer.create(<LoadingIndicator />).toJSON()
  expect(root).toMatchSnapshot()
})
