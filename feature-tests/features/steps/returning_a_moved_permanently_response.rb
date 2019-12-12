class Spinach::Features::ReturningAMovedPermanentlyResponse < Spinach::FeatureSteps
  step 'I make an GET request to "/redirect"' do
    @response = Requests.get("/redirect")
  end

  step 'my response should have status code 301' do
    expect(@response.status_code).to eq(301)
  end

  step 'my response should have a location header with the "http://0.0.0.0:5000/simple_get" URI' do
    expect(@response.location).to eq("#{PROTOCOL}://#{HOSTNAME}:#{PORT}/simple_get")
  end

  step 'my response should have an empty body' do
    expect(@response.body).to be_empty
  end
end
