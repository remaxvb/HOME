require File.expand_path('../boot', __FILE__)

require 'rails/all'

# Require the gems listed in Gemfile, including any gems
# you've limited to :test, :development, or :production.
Bundler.require(:default, Rails.env)

module TRMService
  class Application < Rails::Application
    # Settings in config/environments/* take precedence over those specified here.
    # Application configuration should go into files in config/initializers
    # -- all .rb files in that directory are automatically loaded.

    # Set Time.zone default to the specified zone and make Active Record auto-convert to this zone.
    # Run "rake -D time" for a list of tasks for finding time zone names. Default is UTC.
    # config.time_zone = 'Central Time (US & Canada)'

    # The default locale is :en and all translations from config/locales/*.rb,yml are auto loaded.
    # config.i18n.load_path += Dir[Rails.root.join('my', 'locales', '*.{rb,yml}').to_s]
    # config.i18n.default_locale = :de
    Warden::Manager.after_authentication do |user, auth, opts|
      #auth.cookies - to access cookie
      token = Devise.friendly_token
      user.update_attribute :current_sign_in_token, token
      #session
      auth.env['rack.session'][:sign_in_token] = token
    end

    Warden::Manager.before_logout do |user, auth, opts|
      auth.env['rack.session'].delete :sign_in_token
    end
  end
end
