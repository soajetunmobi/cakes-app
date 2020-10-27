import React from 'react'
import renderer from 'react-test-renderer'
import { ViewReviewPage } from './ViewReviewPage'

describe('ViewReviewPage component', () => {
  it('should render correctly', () => {
    const reviewPage = renderer.create(<ViewReviewPage />).toJSON()
    expect(reviewPage).toMatchSnapshot()
  })
})
