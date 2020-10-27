import React from 'react'
import renderer from 'react-test-renderer'
import Root from './Root'

test('it renders correctly', () => {
  const root = renderer.create(<Root />).toJSON()
  expect(root).toMatchSnapshot()
})
