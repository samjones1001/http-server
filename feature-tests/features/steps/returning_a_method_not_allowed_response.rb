class Spinach::Features::ReturningAMethodNotAllowedResponse < Spinach::FeatureSteps
  step 'I make an GET request to "/get_with_body"' do
    @response = Requests.get("/get_with_body")
  end

  step 'my response should have status code 405' do
    expect(@response.status_code).to eq 405
  end

  step 'my response should have allowed headers of HEAD, OPTIONS' do
    expect(@response.allowed_headers).to contain_exactly("HEAD", "OPTIONS")
  end

  step 'my response should have an empty body' do
    expect(@response.body).to be_empty
  end
end
