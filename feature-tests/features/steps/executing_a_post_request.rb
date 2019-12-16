class Spinach::Features::ExecutingAPostRequest < Spinach::FeatureSteps
  step 'I make a POST with parameters to "/letter"' do
    @response = Requests.post("/letter?giftone=a%bike&gifttwo=a%computer%game&giftthree=some%lego&name=Sam")
  end

  step 'my response should have status code 200' do
    expect(@response.status_code).to eq(200)
  end

  step 'my response body should be the formatted letter' do
    expect(@response.body).to include("a bike")
    expect(@response.body).to include("a computer game")
    expect(@response.body).to include("some lego")
    expect(@response.body).to include("Sam")
  end
end