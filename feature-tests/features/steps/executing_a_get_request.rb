class Spinach::Features::ExecutingAGetRequest < Spinach::FeatureSteps
  step 'I make a GET request to "/poem"' do
    @response = Requests.get("/poem")
  end

  step 'my response should have status code 200' do
    expect(@response.status_code).to eq(200)
  end

  step 'my response should be a poem' do
    expect(@response.body).to eq(File.read("../assets/html/poem.html"))
  end
end