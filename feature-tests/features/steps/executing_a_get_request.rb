class Spinach::Features::ExecutingAGetRequest < Spinach::FeatureSteps
  step 'I make a GET request to "/poem"' do
    @response = Requests.get("/poem")
  end

  step 'my response should have status code 200' do
    expect(@response.status_code).to eq(200)
  end

  step 'my response should have an empty body' do
    expect(@response.body).to eq(File.read("../resources/html/poem.html"))
  end
end