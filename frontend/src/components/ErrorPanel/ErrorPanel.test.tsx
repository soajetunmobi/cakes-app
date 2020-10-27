import React from 'react'
import renderer from 'react-test-renderer'
import { ErrorPanel } from './ErrorPanel'

test('it renders correctly', () => {
  const root = renderer.create(<ErrorPanel />).toJSON()
  expect(root).toMatchSnapshot()
})
