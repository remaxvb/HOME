class Api::UsersController < ApplicationController
  #Skip authenication
  skip_before_action :verify_authenticity_token

  #POST /api/users/user_info
  def info
    user1=User.first
    user2=User.last
    user=user1.merge!
    render :json=> user.as_json
    #TODO Method info: Get specific user info
  end

  #POST /api/users/change_password
  def changePassword
    #TODO Method changePassword: Change password
  end

end
