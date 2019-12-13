require "sinatra"

set :port, 5000

get "/simple_get" do
end

get "/get_with_body" do
  "Hello world"
end

post "/echo_body" do
  request.body
end
