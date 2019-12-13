class Spinach::Features::ReturningANotFoundResponse < Spinach::FeatureSteps
  step 'I make a GET request to "/not_found_resource"' do
    @response = Requests.get("/not_found_resource")
  end

  step 'my response should have status code 404' do
    expect(@response.status_code).to eq 404
  end
end
