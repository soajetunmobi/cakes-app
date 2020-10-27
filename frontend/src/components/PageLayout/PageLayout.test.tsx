import React from 'react'
import renderer from 'react-test-renderer'
import { PageLayout } from './PageLayout'

test('it renders correctly', () => {
  const root = renderer.create(<PageLayout />).toJSON()
  expect(root).toMatchSnapshot()
})
