class Spinach::Features::ExecutingAGetRequestForAnImage < Spinach::FeatureSteps
  BYTES_IN_IMAGE_FILE = File.size("../assets/img/example.jpg")

  step 'I make a GET request to "/image"' do
    @response = Requests.get("/image")
  end

  step 'my response should have status code 200' do
    expect(@response.status_code).to eq(200)
  end

  step 'my response should be an image' do
    puts(@response.body.length)
    expect(@response.body.length).to eq(BYTES_IN_IMAGE_FILE)
  end
end