import React from 'react'
import renderer from 'react-test-renderer'
import { HomePage } from './HomePage'

describe('Home page component', () => {
  test('it renders correctly', () => {
    const root = renderer.create(<HomePage />).toJSON()
    expect(root).toMatchSnapshot()
  })
})
