/* -- az -- */
variable "availability_zone" {
  description = "AZ Setting"
  type = list(string)
  default = ["ap-northeast-2a", "ap-northeast-2c"]
}