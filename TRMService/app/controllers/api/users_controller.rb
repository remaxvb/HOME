class Api::UsersController < ApplicationController
  include Devise::Controllers::InternalHelpers
  #Skip authenication
  skip_before_action :verify_authenticity_token
  #No authentication
  prepend_before_filter :require_no_authentication, :only => [:signin ]

  # POST /api/users/sign_up
  def signup
    user = User.new(signup_params)
    if user.save
      render :json=> {:success=>true, :message => user.as_json}
    else
      warden.custom_failure!
      render :json=> {:success=>false, :message => user.errors}
    end
  end

  #POST /api/users/sign_in
  def signin
    build_resource

  end

  #DELETE /api/users/sign_out
  def signout

  end

  #POST /api/users/info
  def info

  end

  #POST /api/users/change_password
  def changepassword

  end

  #Get signup param from client
  def signup_params
    params.require(:user).permit(:firstname, :lastname, :email, :password,
    :address, :phone, :date_of_birth, :gender) if params[:user]
  end

  def signin_params

  end

end
