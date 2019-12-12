require "httparty"

module Requests
  def self.get(path)
    base_url = "#{PROTOCOL}://#{HOSTNAME}:#{PORT}"
    Response.new(HTTParty.get("#{base_url}#{path}", follow_redirects: false))
  end

  def self.head(path)
    base_url = "#{PROTOCOL}://#{HOSTNAME}:#{PORT}"
    Response.new(HTTParty.head("#{base_url}#{path}"))
  end

  def self.options(path)
    base_url = "#{PROTOCOL}://#{HOSTNAME}:#{PORT}"
    Response.new(HTTParty.options("#{base_url}#{path}"))
  end

  def self.post(path, body="")
    base_url = "#{PROTOCOL}://#{HOSTNAME}:#{PORT}"
    Response.new(HTTParty.post("#{base_url}#{path}", {body: body}))
  end
end
