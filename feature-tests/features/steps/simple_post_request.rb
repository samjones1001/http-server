class Spinach::Features::SimplePostRequest < Spinach::FeatureSteps
  step 'I make a POST with a body to "/echo_body"' do
    @response = Requests.post("/echo_body", "some body")
  end

  step 'my response should have status code 200' do
    expect(@response.status_code).to eq 200
  end

  step 'my response body should equal the body of my request' do
    expect(@response.body).to eq "some body"
  end
end
