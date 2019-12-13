class Spinach::Features::ExecutingASimpleHeadRequest < Spinach::FeatureSteps
  step 'I make a HEAD request to "/simple_get"' do
    @response = Requests.head("/simple_get")
  end

  step 'I make a HEAD request to "/get_with_body"' do
    @response = Requests.head("/get_with_body")
  end

  step 'my response should have status code 200' do
    expect(@response.status_code).to eq(200)
  end

  step 'my response should have no body' do
    expect(@response.body).to be_empty
  end
end
