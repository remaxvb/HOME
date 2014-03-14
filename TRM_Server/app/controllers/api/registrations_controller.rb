class Api::RegistrationsController < ApplicationController
  respond_to :json
  skip_before_action :verify_authenticity_token
  before_filter :fetch_user, :except => [:index, :create]
  def create
    user = User.new(user_params)
    if user.save
      render :json=> {:success=>true, :message => user.id}
    else
    # warden.custom_failure!
      render :json=> {:success=>false, :message => user.errors}
    end
  end
  private

  def user_params
    params.require(:user).permit(:username,:encrypted_password,:email,:fullname) if params[:user]
  end
end