# HTTP Server Spec

A minimal suite of acceptance tests for the HTTP server learning project.

### Installation

Install [Bundler](https://bundler.io/), and then run `bundle install` to clone dependencies.

(Note: if you're using system ruby, you may need to `bundle install --path vendor/bundle`)

### Running the Tests

Start your server on port 5000 (probably in a separate tab/terminal window). Note that this will require different commands, depending on how your project is configured and built.

Once your server is running, `bundle exec spinach` to run the tests.
