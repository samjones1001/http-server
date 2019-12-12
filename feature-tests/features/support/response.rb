class Response

  def initialize(httpartyResponse)
    self.httpartyResponse = httpartyResponse
  end

  def location
    httpartyResponse.headers["location"]
  end

  def allowed_headers
    httpartyResponse.headers["allow"].split(/[ \t]*,[ \t]*/)
  end

  def body
    httpartyResponse.body.to_s
  end

  def status_code
    httpartyResponse.code
  end

  private

  attr_accessor :httpartyResponse

end
